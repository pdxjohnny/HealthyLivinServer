Healthy Livin
---

    For this project I will be building an App to help people shop healthy and
exercise. It will assist them in finding the stores which have the best foods
and where they can buy those foods and what foods are healthy. But how will I
do this? With Object Oriented Programming of course! I have already started my
program and have gotten far into it. In keeping with the theme of abstractions I
have attempted to write a database. So far it is memory resident but I would
like to have it work with files as well. To build the database I will be using
doubly linked lists and trees. I have implemented a 2-3 tree which supports all
of the operations required, so it cannot remove individual items. I am using
this in conjunction with the doubly linked list. The DLL will hold all the data
inserted into the database. The trees are used for indexing into the DLL. I
assign the data in the trees to be references to the data in the nodes of the
doubly linked list. This way lookups can be done quickly when the db is
provided the index to look under. When that that key is not found then the db
runs a search through the DLL to find the value matching the searched for
value. This is meant to provide functionality similar to that of a SQL WHERE.
    The Table class will be derived from the 2-3 tree which eliminates the need
to have a 2-3 tree as data member. I also allows the client to access the
tables directly if they want to preform specific table operations. I have
multiple implementations of a User and therefore User is an abstract class
which provides Marshaling and Unmasrshaling among other things. For example one
method that derivatives of user must implement is the login method. Login is
handled differently depending on what we are using the user for. Also parsers
are an example of my use of interfaces. A user implements Parsable which is an
interface which necessitates that the class implement Marshal and Unmarshal
methods. Classes derived from Parser can take any parsable and parse it or
format it as a string/binary data.
    For the program itself the database uses a tree of trees so the only
requirement left to fulfill is the DLL of arrays. When we grab the records from
the database which are foods or stores whatever they are. We will be getting a
fixed number as a response. Therefore it makes sense that we store that in an
array. When we want to add we should make a call to the database to maintain
consistency.
