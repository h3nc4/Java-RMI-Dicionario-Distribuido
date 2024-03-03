javac -d bin src/**/*
echo "Main-Class: client.Client" > MANIFEST.MF
jar cfm client.jar MANIFEST.MF -C bin/ . README.md LICENSE
echo "Main-Class: server.Server" > MANIFEST.MF
jar cfm server.jar MANIFEST.MF -C bin/ . README.md LICENSE
rm -r MANIFEST.MF bin
