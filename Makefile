start-kafka:
	docker compose -f kafka/common.yml -f kafka/zookeeper.yml -f kafka/kafka_cluster.yml up -d

stop-kafka:
	docker compose -f kafka/common.yml -f kafka/zookeeper.yml -f kafka/kafka_cluster.yml down