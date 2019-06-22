/*
 * Copyright 2019 Gabriel Keller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package itsgjk.juba.entity;

public class JUBAGuildInfo {

    private static String ICON_LINK = "https://cdn.discordapp.com/icons/%s/%s";

    private String id;
    private String name;
    private String icon;
    private String iconLink;
    private String symbol;

    protected JUBAGuildInfo(String id, String name, String icon, String symbol){
        this.id = id;
        this.name = name;
        this.icon = icon;
        iconLink = String.format(ICON_LINK, id, icon);
        this.symbol = symbol;
    }

    /**
     * Discord Snowflake ID of the Guild
     *
     * @return String ID
     */
    public String getId() {
        return id;
    }

    /**
     * Name of the Guild
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * CDN ID for the Guild's icon
     *
     * @return String ID
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Link for the Guild's icon
     *
     * @return String link
     */
    public String getIconLink(){
        return iconLink;
    }

    /**
     * Symbol representing the Guild's currency
     *
     * @return String symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
