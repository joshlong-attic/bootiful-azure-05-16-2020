


## Azure Storage 
The API has changed _considerably_ in the new world


## Azure Service Bus 
You can use the lower-level API, but you're going to probably enjoy the Spring Cloud Stream binder a more. 


## Bootiful SQL Server 

YOu need to create a logical SQL server and then a SQL server database. And then you'll need to deal with the following error. 
The specified database user/password combination is rejected: [S0001][40615] Cannot 
open server 'bootiful-sqlserver' requested by the login. Client with IP address '136.25.20.153' is 
not allowed to access the server. To enable access, use the Windows Azure Management Portal or run 
sp_set_firewall_rule on the master database to create a firewall rule for this IP address or address range. 
It may take up to five minutes for this change to take effect. ClientConnectionId:f10d853e-ed6d-49e9-a7af-7910b6977d61

I fixed this by going to the SQL Server DB, clicking 'Set Firewall' towards the top, and choosing to add a Firewall Rule for my current IP (whatismyip.com).

## 