package com.ruoyi.xindian.wxpay.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

import java.io.Writer;

public class XStreamFactory {
    public static XStream getXStream() {
        final NameCoder nameCoder = new NoNameCoder();
        XStream xStream = new XStream(new XppDomDriver(nameCoder) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                //输出格式化的xml字符串
                //return new PrettyPrintWriter(out, nameCoder) {
                //输出压缩的xml字符串，删除多余的空白符
                return new CompactWriter(out, nameCoder) {
                    boolean cdata = false;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                        // 所有的字符串都加上CDATA标识
                        cdata = "String".equals(clazz.getSimpleName());
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        return xStream;
    }
}
