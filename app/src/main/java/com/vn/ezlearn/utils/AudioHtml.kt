package com.vn.ezlearn.utils

/**
 * Created by FRAMGIA\nguyen.duc.manh on 11/12/2017.
 */

object AudioHtml {

    fun genHtmlAudio(url: String): String {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<audio controls>\n" +
                "  <source src=\"" + url + "\" type=\"audio/ogg\">\n" +
                "</audio>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
    }
}
