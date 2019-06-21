package com.bank;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;

public final class HelloWorldServer {
	 
	public static void helloWorldHandler(HttpServerExchange exchange) {
	    exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "text/plain");
	    exchange.getResponseSender().send("Hello World!");
	}

	public static void main(String[] args) throws ServletException {
//	    int port = 8080;
//	    /*
//	     *  "localhost" will ONLY listen on local host.
//	     *  If you want the server reachable from the outside you need to set "0.0.0.0"
//	     */
//	    String host = "localhost";
//
//	    /*
//	     * This web server has a single handler with no routing.
//	     * ALL urls are handled by the helloWorldHandler.
//	     */
//	    Undertow server = Undertow.builder()
//	        // Add the helloWorldHandler as a method reference.
//	        .addHttpListener(port, host, HelloWorldServer::helloWorldHandler)
//	        .build();
////	    logger.debug("starting on http://" + host + ":" + port);
//	    server.start();
		
		DeploymentInfo servletBuilder = Servlets.deployment()
		        .setClassLoader(HelloWorldServer.class.getClassLoader())
		        .setContextPath("/myapp")
		        .setDeploymentName("test.war")
		        .addServlets(
//		                Servlets.servlet("MessageServlet", BankServlet.class)
//		                        .addInitParam("message", "Hello World")
//		                        .addMapping("/*"),
		                Servlets.servlet("MyServlet", BankServlet.class)
		                        .addMapping("/myservlet")
		                        );

		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();
		PathHandler path = Handlers.path(Handlers.redirect("/myapp"))
		        .addPrefixPath("/myapp", manager.start());

		Undertow server = Undertow.builder()
		        .addHttpListener(8080, "localhost")
		        .setHandler(path)
		        .build();
		server.start();
	}
 
}