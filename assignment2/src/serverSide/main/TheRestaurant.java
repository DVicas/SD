package serverSide.main;


import serverSide.entities.*;
import serverSide.sharedRegions.Bar;
import serverSide.sharedRegions.Kitchen;
import serverSide.sharedRegions.Table;
import clientSide.entities.*;
import clientSide.main.*;
import commInfra.*;


public class TheRestaurant {

	public static void main(String[] args) {
		
		Chef chef;
		Waiter waiter;
		Student[] students = new Student[SimulPar.N]; 
		
		Bar bar;
		Kitchen kitchen;
		Table table;
		
		GeneralRepos repo; 
		
		repo = new GeneralRepos("test");
		
		table = new Table(repo);
		
		//nao passar o monitor table para dentro de outro monitor 
		bar = new Bar(repo, table);
		kitchen = new Kitchen(repo);
		
		chef = new Chef("Chef", kitchen, bar);
		waiter = new Waiter("Waiter", table, kitchen, bar);
		
		for(int i = 0; i < SimulPar.N; i++) {
			students[i] = new Student("Student"+i, i, table, bar);
		}
		
		chef.start();
		waiter.start();
		
		for(int i = 0; i < SimulPar.N; i++) {
			students[i].start();
		}
		
		
		for(int i = 0; i < SimulPar.N; i++) {
			try {
				students[i].join();
			} catch (InterruptedException e) {
				System.out.print("Student "+ i + " left the restaurant");
			}
		}
		
		try {
			waiter.join();
		    } catch (InterruptedException e) {
		        System.out.print("Waiter has left");
		}

		try {
			chef.join();
        } catch (InterruptedException e) {
            System.out.print("Chef has left");
        }
	}

}

        
        
    

       