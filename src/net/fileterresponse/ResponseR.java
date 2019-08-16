package net.fileterresponse;

public class ResponseR {

    //封装response

    /***
     * String headEncoding = ((HttpServletRequest)request).getHeader("accept-encoding");
     *         if (headEncoding == null || (headEncoding.indexOf("gzip") == -1)){
     *             filter.doFilter(request, respone);
     *         }else {
     *             ResponseWrapper mResp = new ResponseWrapper(respone);
     *             filter.doFilter(request, mResp);
     *
     *             BaseResult baseResult=new BaseResult();
     *
     *             byte[] bytes = mResp.getBytes(); // 获取缓存的响应数据
     * //            System.out.println("压缩前大小：" + bytes.length);
     * //            System.out.println("压缩前数据：" + new String(bytes, "utf-8"));
     *
     *             Object parse = JSON.parse(new String(bytes, "utf-8"));
     *             baseResult.setData(parse);
     *             ByteArrayOutputStream bout = new ByteArrayOutputStream();
     *             GZIPOutputStream gzipOut = new GZIPOutputStream(bout); // 创建 GZIPOutputStream 对象
     *             gzipOut.write(baseResult.toString().getBytes()); // 将响应的数据写到 Gzip 压缩流中
     *             gzipOut.flush();
     *             gzipOut.close(); // 将数据刷新到  bout 字节流数组
     *
     *             byte[] bts = bout.toByteArray();
     *           //  System.out.println("压缩后大小：" + bts.length);
     *             respone.setHeader("Content-Encoding", "gzip"); // 设置响应头信息
     *             respone.getOutputStream().write(bts); // 将压缩数据响应给客户端
     *         }
     *
     *
     * */
}
