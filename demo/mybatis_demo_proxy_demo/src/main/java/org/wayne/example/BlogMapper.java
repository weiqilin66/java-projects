package org.wayne.example;

import org.wayne.example.Blog;

/**
 * @Description:   配置文件中引入xml mapper.java的目录并不要求一致
 * @author: lwq
 */
public interface BlogMapper {
    Blog selectBlog(int id);
}
