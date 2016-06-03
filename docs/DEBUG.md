Healthy Livin Debugging + Efficiency Write Up
---

I used intellij as my IDE. I guess its alright but think if I had invested
more time in finding some good vim plugins I could have made vim just as good
for java development. I really fought with intellij a lot early on because it
does not properly create jar files. The only way it would add the manifest is
if I did an explicit rebuild. I ended up having to create a build script that
adds the manifest after intellijs generated ant build runs. I really enjoy that
intellij has built in support for creating tests. I love tests they are a
great way to test for run time bugs. So I wrote some JUnit tests. I would have
liked to write more but I was running out of time so most of the tests I have
are from last week when I was working on the server version and the data
structures. I created a logging class which is derived from PrintStream and I
wraped System.out with it. This allowed me to have lots of debugging statements
that go away if the environment variable `LOG_LEVEL` is not set to `DEBUG`.
This was before I found about that intellij will actually pint all of your
variables inline with the source as you step through it in debugging mode. That
was truly awesome. I would love to have that for all of the languages that I
write in but I have never invested much time into debuggers. Lots of time into
writing test cases though.

This program I admit is a little scandalous. It type casts A LOT. This is
because I was using that array of arrays so much. I highly discourage you from
doing so but I will admit because I am honest that you cant put numbers in as
string fields because of the way that the tree stores everything as Objects and
Comparables. If you put in a number then it will try to compare the other
strings to a number and it will throw a casting error from compareTo. You should
just see and `ERROR: null` message if you try to list after that. I was going
to just wrap the parsing function with a function that takes a string and if
that string is a number it will return undefined instead of the number, but as
I always say, I didn't have time. The error here is once again coming from the
array of arrays. The BasicParser tries to determine which data type it should
be and then it outputs a number when loading from the XML files. This was not
an issue when I used the JavaObjectDump Serializer I created however I wanted
to be sure that someone could hand edit the files in storage because the
requirements were a little vague when it was talking about input from files.

There are also admittedly lots of inefficiencies in this program. I have ideas
on how to fix them, but alas, no time. Well only lots because there are two
major ones. As you may notice as you paruse though the source, there are a
significant amount of for loops where I loop through either DLLs or the Tree23.
For the Tree23 this is especially inefficient because it has to recurse through
the whole tree for each index! Ouch I know I know this is truly terrible. I
would fix this by adding a few data members to the tree. I could have a last
index field along with a last node field and if the index that is trying to be
accessed is close to that last number then just go to the last node and restart
the traverse from there. This would require adding a parent or up pointer to
the tree. The traversal function would also be different, have a different one
entirely because of this. But it would speed the program up a ton by not having
to re index (not the right word) the entire tree. The same concept could be
applied to the DLL and this would be even easier because it already has a
previous reference.

Another thing is that the database using directories and files as storage is
quite a hack. I don't know anything about writing databases though so its what
I had to do to get the job done. I'm pretty sure the correct way to do it is to
seek around a file but I'm unclear as to how to make sure I can expand the size
of a record after doing that. As a side note I used sha256 as the file names.
So I hashed the contents so that when it loads the next time there are now
entries which are completely identical.
