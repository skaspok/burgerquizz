package com.rootsolution.webSocket

import com.rootsolution.Team
import com.rootsolution.TeamName
import com.rootsolution.display.UiClass
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import java.util.*

@Service
class TeamManager(){

    val theTeams: ArrayList<Team> = ArrayList()
    private val logger = LoggerFactory.getLogger(TeamManager::class.qualifiedName)

    fun newTeam(token: String): Team{
        var team: Team
        when {
            theTeams.size ==0 -> {
                logger.info("Mayo : "+token)
                team = Team(TeamName.MAYO,token,0)

            }
            theTeams.size == 1 -> {
                logger.info("Ketchup : "+token)
                team =Team(TeamName.KETCHUP,token,0)

            }
            else -> throw Exception("Teams already registered")
        }
        theTeams.add(team )
        UiClass.instance!!.setTeamImage(team.teamName,true)



        return team
    }

    fun getTeam(token: String): Team?{
        for(team in theTeams){
            if(team.id == token) return team
        }
        return null
    }

    fun removeTeam(token: String){
        throw NotImplementedException()
    }
}


