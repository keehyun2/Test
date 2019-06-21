package com.bank;

import javax.servlet.ServletException;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public final class HelloWorldServer {
	 
	public static void main(String[] args) throws ServletException {
		
		DeploymentInfo servletBuilder = Servlets.deployment()
		        .setClassLoader(HelloWorldServer.class.getClassLoader())
		        .setContextPath("/")
		        .setDeploymentName("QuickBank.war")
		        .addServlets(
//		                Servlets.servlet("MessageServlet", BankServlet.class)
//		                        .addInitParam("message", "Hello World")
//		                        .addMapping("/*"),
		                Servlets.servlet("MyServlet", BankServlet.class)
		                        .addMapping("/bank")
		                        );

		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();
//		PathHandler path = Handlers.path(Handlers.redirect("/myapp")).addPrefixPath("/myapp", manager.start());

		Undertow server = Undertow.builder()
		        .addHttpListener(80, "localhost")
//		        .setHandler(path)
		        .build();
		server.start();
	}
 
}