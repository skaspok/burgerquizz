package com.rootsolution.display

import com.rootsolution.TeamName
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.MediaPlayer
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*

class UiClass : JFrame() {

    companion object {
        var instance: UiClass? = null;
    }

    private val ketchupTeamLabel = JLabel()
    private val mayoTeamLabel = JLabel()
    private val mayoBuzzLabel = JLabel()
    private val ketchupBuzzLabel = JLabel()
    private val mayoScoreLabel = JLabel()
    private val ketchupScoreLabel = JLabel()

    //Videos
    private val videoFrame = JFrame()
    val mediaPlayerComponent = EmbeddedMediaPlayerComponent()

    private val logger = LoggerFactory.getLogger(UiClass::class.qualifiedName)

    init {
        if (instance == null) {
            createUI()
            instance = this
        }
    }

    private fun createUI() {

        isUndecorated = true
        size = maximumSize
        title = "Burger Quizz"
        setLocationRelativeTo(null)

        val scorePanel = JPanel()

        this.layout = GridLayout(3, 1)

        scorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

        this.add(initTeamPanel())
        this.add(initBuzzPanel())
        this.add(initScorePanel())

        initVeoFrame()

        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.isVisible = true

    }


    /**
     * Display active or inactive team image depending on the team connection status
     */
    fun setTeamImage(team: TeamName, active: Boolean) {

        logger.info("setTeamImage " + team.toString() + " " + active)

        val activStr = if (active) "" else "-inactive"
        if (team == TeamName.MAYO) {
            mayoTeamLabel.icon = getScaledBufferedImage("/static/images/mayo" + activStr + ".jpeg")
        } else if (team == TeamName.KETCHUP) {
            ketchupTeamLabel.icon = getScaledBufferedImage("/static/images/ketchup" + activStr + ".jpeg")
        }
        revalidate()
        repaint()
    }

    /**
     * If null : reset the buzzer display
     */
    fun buzz(team: TeamName?) {

        if (team == TeamName.MAYO) {
            playBuzzSound("/static/sounds/mayo-sound.mp3")
            mayoBuzzLabel.isOpaque = true
            mayoBuzzLabel.background = Color.YELLOW
        } else if (team == TeamName.KETCHUP) {
            playBuzzSound("/static/sounds/ketchup-sound.mp3")
            ketchupBuzzLabel.isOpaque = true
            ketchupBuzzLabel.background = Color.RED
        } else {
            mayoBuzzLabel.isOpaque = false
            ketchupBuzzLabel.isOpaque = false
        }
        revalidate()
        repaint()
    }

    private fun playBuzzSound(file: String) {
        val file = ClassPathResource(file).file
        playVideo(file.path)
    }

    fun setScore(teamName: TeamName, value: Int) {
        if (teamName == TeamName.MAYO) {
            mayoScoreLabel.text = value.toString()
        } else if (teamName == TeamName.KETCHUP) {
            ketchupScoreLabel.text = value.toString()
        }
        revalidate()
        repaint()
    }

    /**
     * Play the video
     */
    fun playVideo(path: String) {

        val file = File(path)
        if (file.isFile) {
            if( StringUtils.endsWith(file.name, ".mp3" ) || StringUtils.endsWith(file.name, ".MP3" )){
                videoFrame.isVisible = true
                videoFrame.toBack()
                mediaPlayerComponent.mediaPlayer.playMedia(path)
            }else {
                videoFrame.isVisible = true
                videoFrame.isAlwaysOnTop = true
                videoFrame.toFront()
                mediaPlayerComponent.mediaPlayer.playMedia(path)
            }
        }
        else {
            logger.error("Error with : " + path)
        }
    }

    private fun initVeoFrame() {
        //Video init
        videoFrame.contentPane = mediaPlayerComponent;
        videoFrame.size = maximumSize
        mediaPlayerComponent.mediaPlayer.addMediaPlayerEventListener(BgMediaPlayerEventAdapter(videoFrame))
        videoFrame.isVisible = false
        videoFrame.isUndecorated = true
    }

    private fun initBuzzPanel(): JPanel {
        val buzzPanel = JPanel()
        buzzPanel.layout = FlowLayout()
        buzzPanel.background = Color.black

        val dim = Dimension(300, 150)
        mayoBuzzLabel.preferredSize = dim
        ketchupBuzzLabel.preferredSize = dim
        buzzPanel.setSize(this.width, 50)
        buzzPanel.add(mayoBuzzLabel)
        buzzPanel.add(ketchupBuzzLabel)

//        buzzPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));

        return buzzPanel
    }

    private fun initTeamPanel(): JPanel {
        val teamPanel = JPanel()
        teamPanel.layout = FlowLayout()
        teamPanel.background = Color.black
        teamPanel.size = Dimension(this.width, (this.height * 0.3).toInt())

        setTeamImage(TeamName.MAYO, false)
        teamPanel.add(mayoTeamLabel)

        setTeamImage(TeamName.KETCHUP, false)
        teamPanel.add(ketchupTeamLabel)

//        teamPanel.border = BorderFactory.createLineBorder(Color.PINK, 3);
        return teamPanel
    }

    private fun initScorePanel(): JPanel {
        val scorePanel = JPanel()
        scorePanel.layout = FlowLayout()
        scorePanel.background = Color.black

        setBackground(mayoScoreLabel, "/static/images/backgroundScore.png")
        initScoreLabel(mayoScoreLabel)

        val miams1 = JLabel()
        setBackground(miams1, "/static/images/miams.png");
        setBackground(ketchupScoreLabel, "/static/images/backgroundScore.png")
        initScoreLabel(ketchupScoreLabel)
        val miams2 = JLabel()
        setBackground(miams2, "/static/images/miams.png");
        scorePanel.add(mayoScoreLabel)
        scorePanel.add(miams1)
        scorePanel.add(ketchupScoreLabel)
        scorePanel.add(miams2)
        return scorePanel
    }

    private fun setBackground(label: JLabel, imagePath: String) {
        val file = ClassPathResource(imagePath).file
        val image = ImageIO.read(file)
        label.icon = ImageIcon(image)
    }

    private fun initScoreLabel(label: JLabel) {
        label.font = Font("Serif", Font.PLAIN, 80)
        label.text = "0"
        label.alignmentX = Component.LEFT_ALIGNMENT
        label.foreground = Color.white
//        label.border =  BorderFactory.createLineBorder(Color.PINK, 3);
//        label.border = EmptyBorder(0, 0, 0, -20)
    }


    private fun getScaledBufferedImage(path: String): ImageIcon {
        val file = ClassPathResource(path).file
        val image = ImageIO.read(file)
        val ratio = 0.3//mayo.height/(this.height * 0.3 )
        val scaledImage = image.getScaledInstance((image.width * ratio).toInt(), (image.height * ratio).toInt(), Image.SCALE_DEFAULT)
        return ImageIcon(scaledImage)
    }
}


private fun createAndShowGUI() {

    UiClass()
}

fun display() {
    EventQueue.invokeLater(::createAndShowGUI)
}

internal class BgMediaPlayerEventAdapter(private val jframe: JFrame) : MediaPlayerEventAdapter() {

    @Override
    override fun finished(mediaPlayer: MediaPlayer) {
        jframe.isVisible = false
    }
}