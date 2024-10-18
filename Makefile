start:
	docker compose -f kafka/common.yml -f kafka/zookeeper.yml -f kafka/kafka_cluster.yml up -d
	docker compose -f es/docker-compose.yml up -d

stop:
	docker compose -f kafka/common.yml -f kafka/zookeeper.yml -f kafka/kafka_cluster.yml down
	docker compose -f es/docker-compose.yml down