# gameserver

## outline
- a server to create sessions for online gaming
- initial request looks up port for requested game
- create session for specific port and get session id in return
- further interact with port + session id from all devices willing to join this gaming session

## structure
- Docker container serving requests/ports run on server 185.249.198.58
    - automatically compiling this checked out git repository on Main.java
- actual code for network interaction is in `gameserver/eclipse-prj/src/gameserver`
    - client and server side

## license
- idea, publisher, development: KP-Dev

© 2023, KP-Dev