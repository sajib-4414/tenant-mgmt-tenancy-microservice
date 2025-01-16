https://www.youtube.com/watch?v=F4T_JYO6tpk&ab_channel=ComsystoReplyGmbH
- first have an api gateway, divert traffic like 10% to new microservice(i think identical copy)
- then start to extract service with minimum depencies, low complexity
- extract services with high availability requirements.
- split code base, based on domain 
- extract core services, initially keep the database same for different microservice. add the messaging platform like rabbitmq
- next step: decouple database, most complicated. say 3 microservices are using the same db,
first create one seperate and connect it to one microservice, then gradually. then we connect message queue
so if smeting change in one db, so the other service updates it. 
- Try to have the code divided in packages, make the packages microservice.
Challanges:
- cicd
- monitoring solution
- central logs with elastic search
- message tracing and debugging. which subsystem did the message go.

https://www.youtube.com/watch?v=Inscvakv5XI&ab_channel=CodeOpinion
- divide services based on domain, boundary, context
- still use the same db initially
- instead of model/service call, now u make http call. u can make http, grpc, we will eventually migrate to messaging service
- now for one microservice have one seprate db, get rid of rpc/http call from this service
to other via http/rpc, replace with message broker. 
- now repeat this for other service, have seprate db, have all communicate by mq.
- We write a saga orchestrator handler class. for the transation like place order, then scheule, update inventory, do this asynchronously
with saga. lke we send a message that order created, saga code will see if the other microservice creates
update on the inventory and get back with another message that it is done, then saga will understand ok inventory update done,
now it calls say process shipping, again, message going, shiiping service processes, messages back,
saga sees message back means it done. in the controller/main service,we will just write
like begin transaction, await updateInventory, await updateShipping, endtransaction. the saga will handle the async things,
in a way the controller code will see its almost like synchronous. 
- 