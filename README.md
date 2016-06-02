HealthyLivin Server [![Build Status](https://drone.carpoolme.net/api/badges/pdxjohnny/HealthyLivinServer/status.svg)](https://drone.carpoolme.net/pdxjohnny/HealthyLivinServer)
---

Provides data to the client applications

Building
---

```bash
./scripts/build.sh
```

Deployment
---

Deploy server to private registry, watchtower restarts image when new image is
recieved

```
docker login registry.carpoolme.net
```

Running The CLI
---

```bash
# Listing
java -jar out/artifacts/cli/cli.jar list store --order health --category restaurant -count
java -jar out/artifacts/cli/cli.jar list store --order health --category grocery -count
java -jar out/artifacts/cli/cli.jar list food --order fat --category ante -count
# Adding, end of input is signified by EOF (Ctrl-D)
java -jar out/artifacts/cli/cli.jar add food
java -jar out/artifacts/cli/cli.jar add store
```
