package com.jesusaichao;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 10:29
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao
 */
public class IKTest {
    public static void main(String[] args) throws IOException {
        String word = "java语言是强大的平台";
        StringReader reader = new StringReader(word);
        IKSegmenter segmenter = new IKSegmenter(reader, false);
        Lexeme lexeme = null;
        while ((lexeme = segmenter.next()) != null) {
            System.out.println("lexeme = " + lexeme.getLexemeText()+" ");
        }
    }
}
