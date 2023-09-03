# Machine Setup

The first thing we need to do is to check the setup and download the necessary project to run the tests.

## 1. Check your machine

- [ ] JDK 17 + installed
- [ ] Modern IDE (Intellij, VSCode, etc...)
- [ ] Git
- [ ] Docker
- [ ] Docker Compose

## 2. Download the slides

WIP!

## 3. Download the project


**Step 1: Clone the Project**

To download the project and its source code from the GitHub repository, open your terminal and execute the following command:

```bash
git clone git@github.com:ultimate-engineer/polyglot-nosql-lab.git
```

This will clone the project's repository to your local machine.

**Step 2: Navigate to the Project Directory**

Change your working directory to the project's directory:

```bash
cd polyglot-nosql-lab
```

**Step 3: Download Maven Dependencies**

Execute the following Maven command to download project dependencies, compile the source code, and run tests:

```bash
mvn clean verify
```

Maven will resolve the project's dependencies, build the code, and run the verification process. If all goes well, you'll have successfully downloaded and built the project.

**Step 4: Download Docker Images**

To run the project, you'll need Docker images for various databases. You can download these images using Docker commands.

- Redis:

```bash
docker pull redis
```

- Cassandra:

```bash
docker pull cassandra
```

- MongoDB:

```bash
docker pull mongo
```

- Neo4j:

```bash
docker pull neo4j
```

After executing these commands, Docker will download the required images to your local machine. You'll have Redis, Cassandra, MongoDB, and Neo4j images available for use with the project.

Now you have successfully downloaded the project, its source code, and the necessary dependencies. Additionally, you have the required Docker images to run the project's associated databases. You're ready to work with the Polyglot NoSQL project!