Healthy Livin Analysis
---

    I did both programs 4 & 5 as one so I will be writing the analysis and
debug documents in the same files, just twice as long so that it covers the
same amount as if I had written them separately.
    I think that I did a very good job of keeping this program object
oriented. I had many hierarchies and plenty of classes. When you are grading
you will see that there is a significant amount of unused classes. This is
because I was going to be more adventurous with this program and actually build
and app, which I started to do, but then I realized I really don't don't have
time to do all of that.
    The programs backbone is the database. By writing a database abstraction it
allowed me to further abstract the data structures that I was using. I went
with the doubly linked list and the 2-3 tree again because it was easy to
change it from c++ to java. The way I created the database also satisfied the
requirements. Relational databases are usually made up of tables. My first step
after creating the data structures was to create a table. The table class
needed to store all the records that were put in it but also find them quickly.
For instance when you do a MySQL where query you want to query on an indexed
column, or else it has to look through each record to match the data. I applied
the same concepts in my database design, clearly this is not a good database
but it works for the small scale of this program. Because the table needs to
hold all of the data it is a DLL. This made sense because a DLL is just a bunch
of data you cant get to any data by a key you just have to look through it all.
But because we want the fast lookup we need to use a tree to quickly find keys.
The data that is being held in the table is an array of arrays of type Object.
Which is similar to golang's interface{}. This proved to be very helpful
because I could use those arrays of arrays as a key value store. When inserting
into the table we add the array of arrays to the DLL (which is our self because
a table is a DLL) and then go though the list of indices that we want to
maintain. We look for each index we want to maintain as the key in the array of
arrays and then take its value and add it to the tree which holds the indexes
for that column (key). This way when we go to preform a select the table looks
at its tree of indexes and asks for the tree within that containing the column
we want. Then it looks within that tree to see if the data we want to match is
is stored in any of the columns. If it is we get a reference to the object
which is also stored in the DLL, allowing us to jump straight to that object.
If the index for the key you wish to select on is not present then we churn
though the DLL checking all the objects keys to see if the value matches the
one we want it to.
    Some hierarchies that were created for this program were the CLI hierarcy
and the parsers. The parsers were all derived from BasicParser which provided
the ability to use the array of arrays as a key value store. The Parser class
was an abstract class which facilitated the parsing of parsables. Both
JSONParser extends from parser allowing us to pass any class which implements
Parsable to be parsed by a JSONParser. BasicData in net.carpoolme.healthylivin
is an abstract base class which implements Parsable. In fact it leaves that job
to its first child who is also an abstract class. This first child has the task
of loading us from the database and creating the table with all the indexes we
will need as well as setting up all the protected data members which will be
used by the grandchild. The program as originally designed to be a client and a
server, the client being on an Android phone and the server providing all of
the data. Which is why in net.carpoolme.healthylivin.cli there are the only
grandchildren. If I had continued with the server portion then there would be a
.server package as well which would implement the methods for parsing in and
displaying the various classes to an API. Only the client ones are there and
they handle parsing and displaying to and from the command line. Another place
I used inheritance was in the cli package which is the one the user actually
interacts with. The various commands a derived from the CLICommand class which
does nice things like set up the argument parser (which is derived from a
Tree23). This makes it so I just had to implement the run method if I wanted
that command to be invocable.
