package com.rootsolution.display

import com.rootsolution.TeamName
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import uk.co.caprica.vlcj.player.MediaPlayer
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.MediaPlayerFactory
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import java.awt.*
import javax.imageio.ImageIO
import javax.swing.*
import java.awt.Dimension
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent


class UiClass(title: String) : JFrame() {

    companion object {
        var instance : UiClass? =null;

    }

    private val ketchupLabel = JLabel()
    private val mayoTeamLabel  = JLabel()
    private val mayoBuzzLabel = JLabel()
    private val ketchupBuzzLabel = JLabel()


    //Videos
    private val videoFrame = JFrame()
    val mediaPlayerComponent = EmbeddedMediaPlayerComponent()

    private val logger = LoggerFactory.getLogger(UiClass::class.qualifiedName)

    init {
        if(instance == null){
            createUI(title)
            instance = this
        }
    }

    private fun createUI(title: String) {

        isUndecorated = true
        size = maximumSize
        setLocationRelativeTo(null)

        val scorePanel = JPanel()

        this.layout = GridLayout(3,1)

        scorePanel.setBorder( BorderFactory.createLineBorder( Color.GREEN, 3));

        val mayoLabel = Label()
        mayoLabel.text ="test"

        this.add(initTeamPanel())
        this.add(initBuzzPanel())

        initVeoFrame()

        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.isVisible = true

        val path = "/home/adrien/Downloads/videoplayback.mp4";

        //playVideo("/home/adrien/Downloads/videoplayback.mp4");
    }



    /**
     * Display active or inactive team image depending on the team connection status
     */
    fun setTeamImage(team: TeamName, active: Boolean){

        logger.info("setTeamImage "+ team.toString() + " "+active)

        val activStr = if (active)  "" else "-inactive"
        if(team == TeamName.MAYO){
            mayoTeamLabel.icon = getScaledBufferedImage("/static/images/mayo" +activStr+".jpeg")
        }else if(team == TeamName.KETCHUP){
            ketchupLabel.icon = getScaledBufferedImage("/static/images/ketchup"+activStr+".jpeg")
        }
        revalidate()
        repaint()
    }

    /**
     * If null : reset the buzzer display
     */
    fun buzz(team: TeamName){
        if(team == TeamName.MAYO){
            mayoBuzzLabel.setOpaque(true);
            mayoBuzzLabel.background = Color.YELLOW
        }else if(team == TeamName.KETCHUP){
            ketchupBuzzLabel.setOpaque(true);
            ketchupBuzzLabel.background = Color.RED
        }else{
            mayoTeamLabel.setOpaque( false)
            ketchupBuzzLabel.isOpaque = false
        }
        revalidate()
        repaint()
    }

    /**
     * Play the video
     */
    fun playVideo(path: String){

        videoFrame.isVisible = true
        videoFrame.isAlwaysOnTop = true
        videoFrame.toFront()
        mediaPlayerComponent.mediaPlayer.playMedia(path)
    }


    private fun initVeoFrame() {
        //Video init
        videoFrame.contentPane = mediaPlayerComponent;
        videoFrame.size = maximumSize
        mediaPlayerComponent.mediaPlayer.addMediaPlayerEventListener(BgMediaPlayerEventAdapter(videoFrame))
        videoFrame.isVisible = false
        videoFrame.isUndecorated = true
    }

    private fun initBuzzPanel(): JPanel{
        val buzzPanel = JPanel()
        buzzPanel.layout = FlowLayout()

//        mayoBuzzLabel.setSize(maximumSize)

        val dim = Dimension(300, 150)
        mayoBuzzLabel.preferredSize = dim
//        mayoBuzzLabel.setBorder( BorderFactory.createLineBorder( Color.black, 4));
        ketchupBuzzLabel.preferredSize = dim
        buzzPanel.setSize(this.width,50)
        buzzPanel.add(mayoBuzzLabel)
        buzzPanel.add(ketchupBuzzLabel)

        buzzPanel.setBorder( BorderFactory.createLineBorder( Color.ORANGE, 4));

        return buzzPanel
    }

    private fun initTeamPanel(): JPanel{
        val teamPanel = JPanel()
        teamPanel.layout = FlowLayout()
        teamPanel.size = Dimension(this.width, (this.height * 0.3).toInt())

        setTeamImage(TeamName.MAYO,false)
        teamPanel.add(mayoTeamLabel)

        setTeamImage(TeamName.KETCHUP,false)
        teamPanel.add(ketchupLabel)

        teamPanel.border = BorderFactory.createLineBorder( Color.PINK, 3);
        return teamPanel
    }


    private fun getScaledBufferedImage(path: String): ImageIcon{
        val file = ClassPathResource(path).file
        val image = ImageIO.read(file)
        val ratio = 0.3//mayo.height/(this.height * 0.3 )
        val scaledImage = image.getScaledInstance((image.width*ratio).toInt(), (image.height*ratio).toInt(), Image.SCALE_DEFAULT )
        return ImageIcon( scaledImage )
    }





//    fun Image.toBufferedImage(): BufferedImage {
//        if (this is BufferedImage) {
//            return this
//        }
//        val bufferedImage = BufferedImage(this.getWidth(null), this.getHeight(null), BufferedImage.TYPE_INT_ARGB)
//
//        val graphics2D = bufferedImage.createGraphics()
//        graphics2D.drawImage(this, 0, 0, null)
//        graphics2D.dispose()
//
//        return bufferedImage
//    }
}



private fun createAndShowGUI() {

    val frame = UiClass("Burger quizz")
}

fun display() {
    EventQueue.invokeLater(::createAndShowGUI)
}

internal class BgMediaPlayerEventAdapter(private val jframe: JFrame) : MediaPlayerEventAdapter() {

    @Override
    override fun finished(mediaPlayer: MediaPlayer) {
        jframe.isVisible =false
    }
}