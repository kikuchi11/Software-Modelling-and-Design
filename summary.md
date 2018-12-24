
Software Modelling

Creation of modifiable but abstract representations of a system in order to communicate ideas


UP (Unified Process) : Process of software development

Requirements -> Design -> Implementation -> Test ->Integrate



UP Artifacts

Business Modelling : Domain Model (Objects, Attributes, Associations)

Requirements : Use Case Model (Use Case Diagram, Use Case Text, Operation Contracts) 

Design : Design Model


Operation Contracts : operation, use cases, preconditions, postconditions

system behaviour in terms of state changes to objects in the Domain Model, after a system operation has executed.



Domain Model (Blueprint for a software developer) vs Design Model (Overview of the world)

Design Model : Domain Model + Property, Methods, Privacy Modifiers, Interfaces/Abstract Classes, Data Types


Design Model (Domain Model + Property, Methods, Privacy Modifiers, Interfaces/Abstract Classes, Data Types)

Tips:
Reuse existing models (e.g. Organisational domain model)

Use a category list (e.g. business transactions : sales, payment, reservation)

Identify noun phrases in a use case (e.g. Customer, Cashier, Sale, Item Identifier)

Attributes vs Classes
If the entity is not a number/text value, it's a conceptual class, not an attribute


Actor: a computer system or organisation
	•	Primary Actor: Has user goals (e.g. User, Administrator)
	•	Secondary Actor: Provides a service (e.g. Web Server)
	•	Offstage: Has an interest in the use case but not primary or supporting (e.g. Client Application)


Use Case: Text descriptions of an actor
Can be brief


Sequence Diagram vs Communication Diagram

Sequence
Clearly shows times ordering of messages
Can easily represent the detail of message protocols between objects

Communication
More layout options
Clearly shows relationships between object instances


GRASP : General Responsibility Assignment Software Principles :

Controller Pattern
Class A is responsible for running overall system but delegates events to lower classes

May cause
Too many responsibilities
Low cohesion
Unfocused


Creator Pattern
Class B contains A and is responsible for initialising it

Use when 
The creation process is complex with a large number of parameters


High Cohesion
How focused classes are
Makes classes more understandable and maintainable


Information Expert
Delegate responsibilities to objects which know the most about them
Improves cohesion and reduces coupling


Low Coupling
How independent classes are
Makes classes more modifiable and maintainable


Polymorphism
Allows code to morph in different situations


Protected Variations
How to design overall system so that the variations in one element does not have an undesirable impact on other elements
Create a stable interface


Indirection
Create an intermediate class to mediate between other components so that they are not directly coupled
Higher Complexity in design
Improves low coupling
e.g. Doctor, Patient -> DoctorPatient


Pure Fabrication
Create a class that doesn’t actually represent a concept in the problem domain
Improves high cohesion and low coupling

e.g. service such as receipt printer, create a new class that manages methods of another class


Open Closed Principle 
Software entities should be open for extension but closed for modification


State Machines : shows behaviour of an object (event, state, transition)


Test Driven Development
Write the requirements before coding
Check each requirement in the process
Clarifies software behaviour


Check :
duplicated code
big methods
class with many instance variables
hard coded class
similar subclasses
use of interfaces
high coupling
low cohesion


Software Architecture
High level structures of a software system

Logical Architecture
Packages, subsystems, layers
doesn’t involve networking, physical computers or OS

Layered Architecture
e.g. presentation layer, business layer, persistence layer, database layer

Distributed Architectures
Communicate through a network

Client-Server Architecture
Clients and Servers
Clients send requests to the server
Server processes requests
e.g. web server

Peer to Peer Architecture
Each component works as both a server and client
e.g. torrent

Pipeline Architecture
Filter data, processes it, then write the result


Architectural Analysis : resolution of the system’s non-functional requirements, in the context of the functional requirements

Avoid missing a critical factor in system

Non-Functional Requirements 

Usability : Consistency in the UI

Reliability : Availability

Performance : execution time, recovery time…

Supportability : testability, maintainability


Priorities : 
Inflexible Constraints
security, legal compliance

Business Goals
demo for clients

Other Goals
extensible




