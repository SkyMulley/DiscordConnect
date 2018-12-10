package sky.mulley.DiscordConnect.GeneralLogic;

import sx.blah.discord.api.IDiscordClient;

public class BotInstance {
    private IDiscordClient client;
    public BotInstance(IDiscordClient client) {this.client = client;}
    public IDiscordClient getData() { return client;}
}
