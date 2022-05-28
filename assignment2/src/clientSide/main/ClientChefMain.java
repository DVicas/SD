/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientSide.main;

import clientSide.entities.Chef;
import clientSide.stubs.*;

/**
 * Client side of the Assignment 2 - Chef. Static solution Attempt (number of
 * threads controlled by global constants - ExecConst) Implementation of a
 * client-server model of type 2 (server replication). Communication is based on
 * a communication channel under the TCP protocol.
 */

public class ClientChefMain {

	/**
	 * Main method.
	 *
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		Chef chef; // Reference to the Chef Thread

		BarStub bar; // Reference to the Bar
		KitchenStub kitchen; // Reference to the Kitchen
		TableStub table; // Reference to the Table

		bar = new BarStub(" ", 22150);
		kitchen = new KitchenStub(" ", 22151);

		chef = new Chef("Chef_1", kitchen, bar);

		/* start thread */
		chef.start();

		/* wait for the end */
		try {
			chef.join();
		} catch (InterruptedException e) {
		}
		System.out.println("The Chef 1 just terminated");

		System.out.println("End of the Simulation");

	}
}