package com.rootsolution

class Team(val teamName: TeamName,
           val id: String,
           var score: Int){
}

enum class TeamName(val teamName: String) {
    MAYO("Mayo"),
    KETCHUP("Ketchup");

    override fun toString(): String {
        return teamName
    }

}
