package za.co.admatech.domain.enums;

public enum ProductCategory {
    LAPTOPS("Laptops", "💻", "Portable computers for work and play"),
    SMARTPHONES("Smartphones", "📱", "Latest mobile devices and accessories"),
    COMPONENTS("Components", "🧩", "Internal parts for PC building"),
    GAMING("Gaming", "🎮", "Consoles, peripherals, and accessories"),
    AUDIO("Audio", "🎧", "Headphones, speakers, and sound systems"),
    NETWORKING("Networking", "🌐", "Routers, modems, and Wi-Fi gear"),
    WEARABLES("Wearables", "⌚", "Smartwatches and fitness trackers"),
    STORAGE("Storage", "💾", "SSD, HDD, and cloud devices"),
    SOFTWARE("Software", "🔐", "Licenses, productivity, and security tools");

    private final String displayName;
    private final String icon;
    private final String description;

    ProductCategory(String displayName, String icon, String description) {
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getIcon() { return icon; }
    public String getDescription() { return description; }
}

