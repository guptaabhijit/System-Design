
- CalendarApp is the main driver Class.
- CalendarService is the core service which interacts with repository and models of the code.
- The code is written keeping separation of concerns in mind and following domain driven design principles.
- For concurrency handling, instead of using ArrayList we can use CopyOnWriteArrayList which is thread safe (in-built) and
handle concurrent modifications. We can also add synchronize keywords for any critical section part of code like functions such as
addEvent / deleteEvent. Here multiple threads can access the same data store. Having syncrhonize keyword will help to make sure only single thread access the datastore while adding or deleting events.
- CalendarServiceTest contains unit test for functions such as create, delete, get list of events, conflicting events and recurring events.