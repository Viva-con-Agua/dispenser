docker run --name dispenser-mongo --restart=unless-stopped -d mongo
docker run --name dispenser -p 4002:9000 -d vivaconagua/dispenser:0.1.12 \
	-Dconfig.resource=application.conf \
	-Dmongodb.uri=mongodb://mongo/dispenser
