package com.baizhi;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class HingLightIndex {
    /**
     * 检索结果 高亮显示
     *
     * 分页显示
     *
     */
    @Test
    public void test1() throws IOException, InvalidTokenOffsetsException {
        String keyword="鱼";
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("g:\\lucene\\index2"));

        IndexReader indexReader= DirectoryReader.open(fsDirectory);

        IndexSearcher indexSearcher= new IndexSearcher(indexReader);
        Term term = new Term("context", keyword);
        TermQuery query = new TermQuery(term);
        TopDocs topDocs = indexSearcher.search(query, 10);

        Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<span>", "</span>"),new QueryScorer(query));
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            int docID = scoreDoc.doc;
            Document document = indexSearcher.doc(docID);
            String bestFragment = highlighter.getBestFragment(new StandardAnalyzer(), "context", document.get("context"));
            System.out.println(bestFragment);
        }
    }

    /**
     * 分页显示
     *
     */
    @Test
    public void test2() throws IOException {
        int nowPage=2;
        int pageSize=2;

        String keyword="";
        FSDirectory fsDirectory=FSDirectory.open(Paths.get("g:\\lucene\\index2"));
        IndexReader indexReader =DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        TopDocs topDocs = indexSearcher.search(new TermQuery(new Term("context", keyword)), pageSize);


    }


}
