package com.rootsolution.burgerquizz.display

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors


@Service
class VideoManager {

    private val logger = LoggerFactory.getLogger(VideoManager::class.qualifiedName)

    fun getVideoList(): String {

        logger.info("get and return available video")

        val resource = ClassPathResource("/static/videos")
        val file = resource.getFile()

        val listJsonElt = ArrayList<String>()

        val sb = StringBuilder()
        sb.append("{ ")
        listJsonElt.add("\"/\" : "+ listFilesForFoler(file))

        //Only one level
        for( folder in file.listFiles()){
            if(folder.isDirectory){
                listJsonElt.add( "\"" +folder.name+"\"" +" : "+ listFilesForFoler(folder) )
            }
        }

        return "{ "+ StringUtils.join(listJsonElt,", ") + "}"
    }

    /**
     * return "[file1, file2]"
     */
    private fun listFilesForFoler( folder: File):String{

        if(!folder.isDirectory){
           logger.error("Wrong argument!!")
            return ""
        }

        val listFileName =  Arrays.asList(*folder.listFiles())
                .stream()
                .filter(Predicate { it->it.isFile })
                .map{ "\"" + it.name + "\"" }.collect(Collectors.toList())


        // Didn't manage to make //+ listFileName.joinToString(", ") work
       return "[" + StringUtils.join(listFileName,", ")  + "]"
    }
}