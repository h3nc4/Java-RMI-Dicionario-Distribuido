javac -d bin src/**/*
jar cfe client.jar client.Client -C bin/ client -C bin/ service README.md LICENSE
jar cfe server.jar server.Server -C bin/ server -C bin/ service README.md LICENSE
rm -r bin
