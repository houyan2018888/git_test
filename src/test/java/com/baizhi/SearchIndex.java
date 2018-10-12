package com.baizhi;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 检索索引
 *  1.准备检索的关键词
 *  2.分析处理
 *  3.匹配索引库
 *  4.返回结果
 *
 */
public class SearchIndex {

    @Test
    public void test1() throws IOException {
        //1.准备检索关键词
        String keyword="鲲";
        //2.初始化索引库的存储信息
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("g:\\lucene\\index2"));
        //3.初始化索引读取器
        IndexReader reader = DirectoryReader.open(fsDirectory);
        //4.初始化索引检索器
        IndexSearcher searcher = new IndexSearcher(reader);
        //5.检索索引库
            //创建基于词元的查询对象 封装查询条件  域名+关键词
        TermQuery query = new TermQuery(new Term("context", keyword));
            //进行检索 参数一：检索条件 参数二：匹配结果的条数
        TopDocs topDocs = searcher.search(query, 10);

        //检索结果
        System.out.println("查询到的命中率："+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("查询到的权重得分："+scoreDoc.score);
            int doc = scoreDoc.doc;
            Document document = reader.document(doc);
            System.out.println("ID: "+doc+" 值："+document.get("context"));
        }
        //释放资源
        reader.close();
    }
}
