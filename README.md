# pswatcher
Java program that monitors F1 grades in PowerSchool and sends Telegram messages containing any updates.

### Run via Docker
```shell script
docker pull dnsge/pswatcher:1.0
docker run --name pswatcher --env-file config.env dnsge/pswatcher:1.0
```

### Environment Variables For Configuration:
- USERNAME
    - Username to log into PowerSchool with
- PASSWORD
    - Password for the corresponding username
- PS_URL
    - URL to PowerSchool web server
- BOT_TOKEN
    - Telegram bot token
- CHAT_ID
    - Telegram Chat ID to send messages to
- USER_AGENT
    - User-Agent header for requests to the PowerSchool web server
    - Default of `Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/71.0`
- DELAY
    - Delay in seconds in between grade refreshes
    - Default of `1800` (30 minutes)

### Disclaimer
This program uses a PowerSchool API powered by web scraping. It may not return the most accurate results for certain configurations of PowerSchool.