# Uruchomienie brokera kolejek
## Wykorzystranie activeMQ ponieważ wersja artemis nie działała
docker run -it --rm -p 61616:61616 -p 8161:8161 symptoma/activemq:latest