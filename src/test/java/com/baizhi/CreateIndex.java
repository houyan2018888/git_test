package com.baizhi;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 创建索引
 *
 */
public class CreateIndex {

    @Test
    public void test1() throws IOException {
        // 1.指定索引存储的位置  可基于内存 可基于磁盘储存
        //RAMDirectory directory = new RAMDirectory();
        FSDirectory directory = FSDirectory.open(Paths.get("g:\\lucene\\index2"));

        // 2.创建分析器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        //3.创建索引写入配置对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        // 4.创建索引写入器
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 5.创建索引数据
        Document document = new Document();
        // 6.添加域 文本域
        document.add(new TextField("context", "北冥有鱼，其名为鲲", Field.Store.YES));
        Document document1 = new Document();
        document1.add(new TextField("context", "鲲之大，不知其几千里也", Field.Store.YES));

        indexWriter.addDocument(document);
        indexWriter.addDocument(document1);
        indexWriter.commit();
        indexWriter.close();
    }

}
