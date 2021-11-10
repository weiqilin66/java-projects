package org.wayne.design.actor.p0;

import org.wayne.design.actor.p0.entity.Middleware;
import org.wayne.design.actor.p0.entity.RoleCheckMiddleware;
import org.wayne.design.actor.p0.entity.ThrottlingMiddleware;
import org.wayne.design.actor.p0.entity.UserExistsMiddleware;
import org.wayne.design.actor.p0.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // 核心代码 构造处理者责任链
        Middleware middleware = new ThrottlingMiddleware(2);
        middleware.linkWith(new UserExistsMiddleware(server))
                .linkWith(new RoleCheckMiddleware());
        server.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException {
        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);
    }
}
