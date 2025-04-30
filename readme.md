# Server Startup Instructions

1. Use `docker-compose.yml` and hit the **play button** to start the **Kafka server**.
2. Start the **MongoDB server** (**also add MongoDB to the `docker-compose.yml`** — don't forget!).
3. Start the **Backend application**.
4. Start the **Frontend application**.

---

## 📋 Total Number of Servers:

| Server    | Address            |
|-----------|--------------------|
| Frontend  | `http://localhost:3000` |
| Backend   | `http://localhost:8080` |
| Kafka     | `localhost:9092`         |
| MongoDB   | `localhost:27017`         |


## Some Crucial information about Kakfa 
### Kafka in Docker: KRaft vs. ZooKeeper

### **KRaft Mode (New Approach)**
- **Eliminates ZooKeeper**: Uses Kafka's built-in **KRaft protocol** (Raft consensus) for metadata/cluster coordination (Kafka 3.3+).
- **Roles**:
    - **Controllers**: Manage metadata (replaces ZooKeeper).
    - **Brokers**: Handle data storage/production.
    - *Single node can combine both roles* (`controller,broker`).
- **Config**:
  ```yaml
  KAFKA_ENABLE_KRAFT=yes
  KAFKA_CFG_PROCESS_ROLES=controller,broker
  KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@kafka:9093


## **Key Differences**
| Feature               | KRaft (New)                               | ZooKeeper (Old)                     |  
|-----------------------|-------------------------------------------|-------------------------------------|  
| **Dependency**        | Self-managed (no external service)        | Requires ZooKeeper cluster          |  
| **Roles**             | Unified roles (controller + broker)       | Separate components                 |  
| **Complexity**        | Simpler deployment                        | More components to deploy/maintain  |  
| **Resource Overhead** | Lower (single process)                    | Higher (multiple services)          |  