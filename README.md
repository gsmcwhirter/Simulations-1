# Plants, Zombies, and Humans

This project is to complete a simulation of a world where plants, zombies, and humans may (or may not, in the end) coexist. The simulation will make substantial use of interfaces and abstract classes to illustrate how they might be employed productively.

## Demo

In the folder, there is a BlueJ project called `demo`. Open that project in BlueJ, right-click on the SimulationDriver class, and run the simulation to see what should happen.

In the demo, red squares are zombies, blue squares are humans, green squares are plants, and brown squares are empty ground. If two or more entities are on top of each other, then their colors are averaged to produce a new color. There are also some black (and non-black but dark) squares. These indicate that the terrain is impassable in those locations.

Restart the BlueJ kernel after a little while and re-run the simulation. Look for any major differences in the results. Repeat a couple of times.

1. Write down some observations that you made regarding your (and other students') simulation runs.

This is the simulation your will be working on implementing for yourself.

## Understanding the Layout

Opening the other project in the folder (`simulation`) in BlueJ, you will see that there are a lot of pieces, many of which are different types. Open up each piece and make a list (in a GoogleDoc or something similar) of the following:

1. Which items are classes?
2. Which items are interfaces?
3. Which classes are abstract?
4. For each class, what are all of its superclasses (in order)?
5. For each class, which interfaces does it implement (be sure to list interfaces from superclasses)?
6. For each class, which methods MUST it implement in order for it to compile (taking into account methods implemented by superclasses)?

Many of the pieces you will have no need to edit (though you might want to read through them). The pieces we will be focusing on are the Human, Plant, Zombie, DirtTerrain, MyWorld, and SimulationTester classes.

## Setting up the Tester

While we are getting everything working, we won't be trying to run the full simulation. Instead, we will write some test methods in the `SimulationTester` class. Let's start by just getting a `main` method set up. Later, we will add other static methods and call them in the `main` to test the various aspects of the simulation. Right now, just make the `main` method print "Starting tests…."

## Creating the Terrain

The first class we will finish implementing is the DirtTerrain. Try compiling just that class, without any changes, and pay attention to the error message that you get.

Now add a new method to the class to fix the error. For now, just make it return a dummy value of true and make sure it compiles (that is, make sure a new error message comes up).

It is a good idea to include the following line above your method header:

    @Override //implements abstract
    
This is an *annotation*. It tells the compiler that you are intending to override a method. If you happen to mess up the signature (by mis-spelling, or having different parameters), then it will stop you until you fix it.

The second error we are seeing is a case of this. It is telling you that the method `setPassable` that was supposed to override an abstract method in `Terrain` isn't doing so. Fix it so that the `DirtTerrain` class will compile.

Once the class compiles, we can add some code to test it. In the `SimulationTester` class, let's add a static method `testDirtTerrain`. In this method, we want to do the following:

0. Print out that the terrain test is starting.
1. Create a new DirtTerrain object assigned to a Terrain variable. Pass in null as the parameter to the constructor.
2. Print the object
3. Print out whether the object is passable or not (with a nice leading message — should not just be true/false)
4. Ask the object to randomly mutate, and print out the result (with a nice leading message — should not just be true/false)
5. Print out whether the object is passable or not (with a nice leading message — should not just be true/false)
6. Set the object to be impassable and print out the result (with a nice leading message — should not just be true/false)
7. Print out whether the object is passable or not (with a nice leading message — should not just be true/false)

Then add a line to the `main` method that calls the terrain testing method. Run the main method to make sure it works.

Now, we should make the random mutation method actually have a chance at randomly mutating the object. Essentially, we want the computer to roll a die, and if the result is small enough, mutate the object. The threshold for "small enough" is already defined for is in the `MUTATION_CHANCE` constant.

So, the plan is, if a random event happens that is small enough, then toggle the passability from true to false or false to true, and then return true. If the event is not small enough, then we don't change the passability and return false.

Once those changes are made, re-run the test class a few times (maybe editing the `MUTATION_CHANCE` temporarily to give it better odds) and make sure everything works properly.
   
## Creating the Actors

For now we're only going to have one terrain type, but we will have several types of Actors.

### Plants

The `Plant` class represents a plant growing on the map of the simulation. Each plant has a certain size and can grow or shrink in response to events in the world.

To start work on finishing the `Plant` class, we need to add its instance variables. We first need a variable to represent the `size` of the plant. All plants start at size 2.

Plants can also spawn new plants when they get big enough. To represent the spawn conditions, we also need instance variables to represent the `spawnThreshold` (how big the plant needs to be to spawn another — default 10), `spawnRange` (how far away a new plant can spawn from this one — default 2), and the `spawnProbability` (likelihood of actually spawning once the threshold is reached — default 1.0).

Now that we have the instance variables, we can start implementing some of the missing methods. Comparing the list of methods you made earlier with those already included in `Plant`, we should be able to identify that we still need to implement the following mehods:

    @Override //implements abstract
    public void act()
    
    @Override //implements abstract
    public boolean isDead()
    
    @Override //implements Growable interface
    public boolean shrink()
   
    @Override //implements Growable interface
    public boolean grow()
   
    @Override //implements Growable interface
    public int getSize()
   
    @Override //implements Edible interface
    public boolean beEaten()
    
    @Override //implements Edible interface
    public int getFoodAmount()
    
Many of these should be straightforward to implement. Some relevant information is below (but you may have to figure out some of the rest):

- A plant is considered dead if its size is reduced past 0.
- A plant is always able to grow, and when it does, its size should increase by 1.
- A plant is always able to shrink, and when it does, its size should decrease by 1.
- A plant can always be eaten, and when it is, it shrinks.
- A plant has a nutritional value of 2 at all times.
- When a plant acts, it first checks to see if it can spawn (depends on `spawnThreshold`). Then it sees if it actually does spawn (depends on `spawnProbability`). Whether or not it actually spawns, if it met the threshold, it shrinks. Then it checks to see if it is dead with the following code:

        if (isDead())
        {
            getWorld().kill(this);
        }
    
Finally, the `Plant` class is also missing a constructor. Since it inherits from `Actor` and `Actor` has only a 1-parameter constructor, the `Plant` class also needs to implement a constructor. This constructor doesn't necessarily need to have only 1 parameter, but that will make it easiest for us. So, we should add a 1-parameter constructor for the `Plant` class that passes the required information up the inheritance hierarchy to the `Actor` constructor.

Now we should be able to compile the `Plant` class and start writing a few tests. In the `SimulationTester` class, let's add a static method `testPlant`. In this method, we want to do the following:

0. Print out that the plant test is starting.
1. Create a new Plant object assigned to a Plant variable called `p`. Pass in null as the parameter to the constructor.
2. Print the object `p`
3. Assign the object in `p` to a new Growable variable called `g`. 
4. Print the object `g`
5. Assign the object in `g` to a new Edible variable called `ed`.  You will have to cast it. (Why is this safe in this instance, but not a good idea in general?)
6. Print the object `ed`
7. Print out whether or not `p` is dead (with a nice leading message — should not just be true/false)
8. Print out `g`'s size (with a nice leading message — should not just be a number).
9. Tell `g` to grow and print out the result (with a nice leading message — should not just be true/false).
10. Print out `g`'s size (with a nice leading message — should not just be a number).
11. Tell `g` to shrink and print out the result (with a nice leading message — should not just be true/false).
12. Print out `g`'s size (with a nice leading message — should not just be a number)
13. Tell `g` to shrink and print out the result (with a nice leading message — should not just be true/false)
14. Print out `g`'s size (with a nice leading message — should not just be a number)
15. Tell `ed` to be eaten twice, and print out the result each time (with a nice leading message — should not just be true/false)
16. Print out `g`'s size (with a nice leading message — should not just be a number)
17. Print out whether or not `p` is dead (with a nice leading message — should not just be true/false)
18. Print out `ed`'s nutritional value (with a nice leading message — should not just be a number)

Then add a line to the `main` method that calls the terrain testing method. Run the main method to make sure it works. If it doesn't, go back and fix things.

1. Explain the output of printing `p`, `g`, and `ed`.
2. Why are we able to print a `Growable`, even though it doesn't list a `toString()` method?
3. Why couldn't we print out `ed`'s size?
4. Why couldn't we print out whether `g` or `ed` was dead?
5. Did we miss any methods in our testing? If so, which one(s)?
6. For each method missed (if any), try to explain why we omitted it.

### Humans

Next, we will finish implementing the `Human` class. 

Like `Plant`, we first need to add its instance variables. Some of these will be familiar, but most will be different.

- `Human`s have a movement `speed`. The default value is 1.
- `Human`s have a `foodLevel` representing the amount of food they have gathered/stored (default 5).
- `Human`s can starve if their `foodLevel` isn't high enough. They should keep track of their `starvingTurns` (turns that they have been starving in a row — default 0) as well as their `timeToStarve` (the number of turns they can be starving before dying — default 4).
- `Human`s can spawn new `Human` objects if they have enough food. The amount of food required is their `spawnThreshold` (default 7).
- When `Human`s are eaten by a `Zombie`, they spawn a new `Zombie`. But if several `Zombies` try to eat the same `Human`, we don't want more than one new `Zombie` to spawn. So we need to keep track of whether or not a `Human` has `alreadyDied` and spawned a `Zombie` (default false).

Once we have the instance variables set up, we need to add a constructor like we did for the `Plant` class.

Then we should work on implementing the various instance methods. The first four are virtually identical to the equivalent `Plant` methods:

    @Override //implements abstract
    public void act()
    
    @Override //implements abstract
    public boolean isDead()
   
    @Override //implements Edible interface
    public boolean beEaten()
    
    @Override //implements Edible interface
    public int getFoodAmount()

To implement these, we need the following information:

- On a turn, a `Human` first consumes one level of food. Then it checks to see if it is starving or not. If it is, it adds a day to the time it has been starving. If not, it resets the counter of days starving to 0. Then it checks to see if it should spawn another human. Finally, it checks to see if it is dead, and if it is, reports its death (see the code from the `Plant` class for how to do this).
- A `Human` is considered dead if it has been starving at least as many turns as the `timeToStarve` threshold.
- A `Human` has a nutritional value of 1.
- When a `Human` is eaten, first it reports to the world that it is dead (`getWorld().kill(this);`). Then, if it has not already died, it now has, and returns true. If it has already died, it returns false.
    
The rest of the methods required are new, because `Human` implements different interfaces than `Plant` does (in particular, `Movable` and `CanEat`). These interfaces require that we implement the following methods:

    @Override //implements Movable interface
    public int getSpeed()
    
    @Override //implements Movable interface
    public boolean move()
    
    @Override //implements CanEat interface
    public boolean isStarving()
    
    @Override //implements CanEat interface
    public boolean eat()
    
To implement these, the following information is relevant (though you may have to supply more yourself):

- A `Human` is starving if its `foodLevel` is non-positive.
- `Human`s move just like `Zombie`s. In your GoogleDoc (or similar document), explain what happens when either one is told to move.
- `Human`s eat similarly to `Zombie`s, except `Human`s eat `Plant`s, and a new `Human` is not spawned when a `Plant` is eaten. In your GoogleDoc (or similar document), explain what happens when a `Zombie` is told to eat and explain what happens when a `Human` is told to eat.

Now we should be able to compile the `Plant` class and start writing a few tests. In the `SimulationTester` class, let's add a static method `testHuman`. In this method, we want to do the following:

0. Print out that the human test is starting.
1. Create a new `Human` object assigned to a `Human` variable called `h`. Pass in `null` as the parameter to the constructor.
2. Print the object `h`
3. Assign the object in `h` to a new `Movable` variable called `m`. 
4. Print the object `m`
5. Assign the object in `m` to a new `Edible` variable called `ed`.  You will have to cast it. (Why is this safe in this instance, but not a good idea in general?)
6. Print the object `ed`
7. Assign the object in `h` to a new `CanEat` variable called `eat`. You do not have to cast it. (Why not?)
8. Print the object `eat`
9. Print out whether or not `h` is dead (with a nice leading message — should not just be true/false)
10. Print out `ed`'s nutritional value (with a nice leading message — should not just be a number)
11. Print out `m`'s speed (with a nice leading message — should not just be a number)
12. Print out whether `eat` is starving or not (with a nice leading message — should not just be true/false)
13. Tell `h` to consume food several times. After each time, print out whether `eat` is starving or not (with a nice leading message — should not just be true/false). You should do this until `eat` is in fact starving.

Then add a line to the `main` method that calls the terrain testing method. Run the main method to make sure it works. If it doesn't, go back and fix things.

1. Did we miss any methods in our testing? If so, which one(s)?
2. For each method missed (if any), try to explain why we omitted it.
    
### Zombies

Our last `Actor` subclass is the `Zombie`. Much more of this class is already implemented for you. The only things left to implement are:

    @Override //implements abstract
    public void act()
    
    @Override //implements abstract
    public boolean isDead()
    
Both of these methods work nearly identically to the `Human` versions. The major difference is that for `Zombie` objects, we don't spawn new ones in the `act` method, and on any turn that a `Zombie` is starving, it shrinks.

Once the `Zombie` class compiles, you should add appropriate tests. These should be a combination of those from `Plant` and `Human` (selecting the tests that are relevant to the appropriate interface implementations). Part of your grade will depend on the tests you select and write for this portion of the project.

## Creating the World

Now that we have all the terrains and actors set up, we can work on the world for the simulation.
The world we will be editing is called MyWorld. A world object is responsible for coordinating the behaviors of all of the actors and updating the state of things after each turn.

Before we finish the methods for our simulation, we need to add a few constants that are used in the code at various places.

Add the following *private* (static) constants:

1. MAX_STEPS — represents how many steps the simulation should run, at maximum. Set to a value of 3 for now.
2. MAP_SIZE — an array representing the dimensions of the map.  Set to {10, 10} for now.
3. DEFAULT_ZOMBIES — represents how many zombies to spawn by default. Set to a value of 15 for now.
4. DEFAULT_HUMANS — represents how many humans to spawn by default. Set to a value of 15 for now.
5. DEFAULT_PLANTS — represents how many plants to spawn by default. Set to a value of 35 for now.

Now we can start implementing some of the missing methods. First, for testing purposes, we want to implement an overloaded `init` method. Overloading the init method to accept parameters for the number of humans, zombies, and plants will let us more easily test our code. The following is the method header we would like to have.

    public void init(int zombies, int humans, int plants)
    
This init method needs to do a few things. First, it needs to generate terrain squares for every square on the map. Second, it needs to spawn the zombies for the map. Third, it should spawn the humans for the map, and finally, it should spawn the plants for the map.

Having to do so much in one method is a ideal time to create private helper methods that take care of each task separately.

This means we want to create methods with the following signatures, and call them inside our overloaded init method.
    
    private void generateTerrain()
    
This `generateTerrain` method will create a new dirt object for each cell on the map. We will use nested loops to calculate the coordinates of each map square, use `Cell.getOrCreateCell` to get the cell for that square, set the terrain's location to that Cell, and then add the terrain object to the world's map.

The other three helper functions we should create are quite similar to each other. Their signatures are:
    
    private void generateZombies(int howMany)
    
    private void generateHumans(int howMany)
    
    private void generatePlants(int howMany)

Each of the other three methods will create a certain number of each object to exist in the world at random locations. In each of the methods, we have to get the right number of random map coordinates and add an object of the appropriate type to those coordinates.

The only major difference between the three happens in the `generatePlants` method. Unlike other actors, we don't want two plants to be on the same location. So, if the plant was not added to the location it should have been, we need to make sure we try to add more. This technique is guaranteed to stop eventually so long as we don't try to add more plants than there are locations, but it might take a while. 

1. Can you think of a better way to place the right number of plants at random?

These three helper methods should call the appropriate methods from the `World` class to add the new object. However, two of those methods from the `World` class won't yet work correctly.

1. Why won't the `addZombie(int x, int y)` etc. methods in the `World` class work correctly yet?

We can fix this by implementing some of the missing abstract methods from `World`. Add the necessary method headers for `addZombie` and `addHuman` to `MyWorld` so that we can work on implementing them. These two methods behave largely like the `addPlant(Cell loc)` method. Using the `addPlant(Cell loc)` method as a guide, implement the `addZombie` and `addHuman` methods. Think about what kinds of things `Zombie`s and `Human`s are and do. Also remember that we don't mind if two humans or zombies occupy the same location.
    
To actually create a `MyWorld` object we are missing a few methods still. Several of these are used by the `World` class's `act` method to actually move the simulation along. These are:

    @Override //implement abstract
    public void actAll()
    
    @Override //implement abstract
    public void moveAll()
    
    @Override //implement abstract
    public void growAll()
    
    @Override //implement abstract
    public void eatAll()
    
In each of these, the general idea is to just iterate over the appropriate list and tell each member of the list to behave appropriately. In theory, we could have our choice of how to accomplish that —  using either a standard `for` loop for a for-each loop. In two of the cases, however, we must use a standard `for` loop. The reason for this is that we will get a `ConcurrentModificationException` if we try to add things to a list we are iterating over using a for-each loop, and the `act` and `eat` methods can both spawn new creatures into the world. So, for `eatAll` and `actAll`, we must use a standard `for` loop, but for `growAll` and `moveAll` we can (and should, for practice) use for-each loops.

In addition to actually causing the appropriate behaviors to occur in each method, we also want to print out a debugging message, indicating how many total entities there were with that behavior profile, and out of those, how many actually successfully completed the behavior (so, for instance, 20 out of 50 movables might have actually moved).

Finally, in order to get `MyWorld` to compile, we need to implement one last missing abstract method.
This is one version of the `isPassable` method. This version simply reports whether a given cell is passable or not. So, it should go through all blocks at the given location. If any of them is not passable, then it should return false. Otherwise, it should return true.

Now we should be able to compile the `MyWorld` class (and, in fact, compile everything in the simulation at this point). Before we test the whole thing graphically, we should write some tests to make sure the world seems to work correctly as well.

It is up to you what tests to write exactly. Try to test as many things as possible in as many ways as is reasonable (while being able to tell if the methods are working correctly or not based on the output). Part of your grade will depend on the tests you select and write for this portion of the project.

## Combining Everything

Now that everything is implemented and should work, you can test it out! 

In the MyWorld class, change MAP_SIZE to be {40,40}, MAX_STEPS to be 500, DEFAULT_ZOMBIES to be 150, DEFAULT_HUMANS to be 150, and DEFAULT_PLANTS to be 350.

The `SimulationDriver` class connects your MyWorld class to the GUI provided. Try compiling everything and running the `SimulationDriver`'s `main` method. Make sure it behaves approximately like the demo. If not, be sure to go back and try to fix it.

## Exploration

Make a copy of your simulation folder. 
Then, in the copy, change the rules of the game and see what kind of effects happen in the simulation.

Perhaps try adding new terrains with different effects / mutation chances / default passability. Or, whatever else you can think of.

