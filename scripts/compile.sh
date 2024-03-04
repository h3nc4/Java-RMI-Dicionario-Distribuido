javac -d bin src/**/*
jar cfe client.jar client.Client -C bin/ . README.md LICENSE
jar cfe server.jar server.Server -C bin/ . README.md LICENSE
rm -r bin
