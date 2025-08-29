package com.mental.healthsupport.model

/**
 * Created by Charles Raj I on 25/08/25
 * @project Mental Health Support
 * @author Charles Raj
 */

data class ChatNode(
    val id: Int,
    val message : String,
    val options: List<ChatOption> = emptyList(),
    val sender: Sender = Sender.BOT
)

data class ChatOption(
    val label: String,
    val nextId: Int
)

enum class Sender{
    BOT,
    USER
}

object ChatDataProvider{

    fun getAllChats() : List<ChatNode> {

        return listOf(

            ChatNode(
                id = 1,
                message = "Hey there! 👋 How are you doing today?",
                options = listOf(
                    ChatOption("Just Casual Talk", 2),
                    ChatOption("Need Help", 10),
                    ChatOption("Not Feeling Good", 20),
                    ChatOption("Emergency", 30)
                )
            ),
            ChatNode(
                id = 2,
                message = "Nice to hear that! 😊 What would you like to talk about?",
                options = listOf(
                    ChatOption("Campus Life", 3),
                    ChatOption("New Friends", 4),
                    ChatOption("Stress", 5)
                )
            ),
            ChatNode(
                id = 3,
                message = "Campus life can be exciting and overwhelming. Any fun experiences to share? 🏫",
                options = listOf(
                    ChatOption("Yes!", 6),
                    ChatOption("Not really", 7)
                )
            ),
            ChatNode(
                id = 4,
                message = "Making friends in a new country is a journey. How’s it going for you?",
                options = listOf(
                    ChatOption("Great!", 6),
                    ChatOption("Hard to connect", 8)
                )
            ),
            ChatNode(
                id = 5,
                message = "Stress is totally normal. Want to talk about what’s causing it?",
                options = listOf(
                    ChatOption("Yes", 9),
                    ChatOption("Not now", 40)
                )
            ),
            ChatNode(
                id = 6,
                message = "That’s wonderful to hear! Keep enjoying your time and take care. 😊"
            ),
            ChatNode(
                id = 7,
                message = "It’s okay to take your time. Everyone adjusts differently. If you need ideas to get involved, I'm here. 💡",
                options = listOf(
                    ChatOption("Sure, help me!", 9),
                    ChatOption("Maybe later", 40)
                )
            ),
            ChatNode(
                id = 8,
                message = "You’re not alone. Many students feel the same at first. Try joining clubs or online communities on campus. 🤝"
            ),
            ChatNode(
                id = 9,
                message = "You can try journaling, walking, or reaching out to friends. Also, check this free guide: https://www.mind.org.uk/information-support/tips-for-everyday-living/",
                options = listOf(
                    ChatOption("Thanks, that helps", 40),
                    ChatOption("Still struggling", 10)
                )
            ),

            // Need Help Branch
            ChatNode(
                id = 10,
                message = "Sure! I’m here for you. What kind of help do you need?",
                options = listOf(
                    ChatOption("Academic Stress", 11),
                    ChatOption("Homesickness", 12),
                    ChatOption("Financial Stress", 13),
                    ChatOption("Anxiety or Depression", 14)
                )
            ),
            ChatNode(
                id = 11,
                message = "You can talk to your academic advisor or use resources like https://tutorme.com or https://khanacademy.org. Try breaking tasks into small wins.",
                options = listOf(ChatOption("Got it, thanks!", 40))
            ),
            ChatNode(
                id = 12,
                message = "Homesickness hits everyone. Try staying in touch through video calls and keeping reminders of home nearby.",
                options = listOf(ChatOption("Thank you!", 40))
            ),
            ChatNode(
                id = 13,
                message = "Many universities offer emergency financial aid or part-time job support. Check with your financial aid office or use https://www.campusaccess.com/jobs",
                options = listOf(ChatOption("Will do!", 40))
            ),
            ChatNode(
                id = 14,
                message = "I'm really sorry you’re feeling this way. Please talk to a counselor. Most colleges have free therapy services. Here’s one: https://togetherall.com",
                options = listOf(ChatOption("I'll check it out", 40))
            ),

            // Not Feeling Good Branch
            ChatNode(
                id = 20,
                message = "I’m really sorry to hear that. Want to talk more about what’s bothering you?",
                options = listOf(
                    ChatOption("I feel lonely", 21),
                    ChatOption("Too much pressure", 22),
                    ChatOption("Can't sleep", 23)
                )
            ),
            ChatNode(
                id = 21,
                message = "Loneliness is common. Try attending events or reaching out to support groups. Even talking helps. You matter. 💛",
                options = listOf(ChatOption("Thank you for being here", 40))
            ),
            ChatNode(
                id = 22,
                message = "Pressure can build up quickly. Try organizing your day, taking breaks, and don't hesitate to ask for help from professors or friends.",
                options = listOf(ChatOption("I'll try this", 40))
            ),
            ChatNode(
                id = 23,
                message = "Sleep problems can stem from stress. Try mindfulness apps like Headspace or Calm. Avoid screens 1 hour before bed.",
                options = listOf(ChatOption("Thank you", 40))
            ),

            // Emergency Branch
            ChatNode(
                id = 30,
                message = "I'm here for you. If this is an emergency, please call emergency services or a helpline right now.",
                options = listOf(ChatOption("Call Helpline", 31), ChatOption("I’m safe but need urgent talk", 32))
            ),
            ChatNode(
                id = 31,
                message = "Here are some numbers:\n• Canada: 1-833-456-4566\n• India: 9152987821\n• USA: 988\n\nYou are not alone. Someone will help. ❤️"
            ),
            ChatNode(
                id = 32,
                message = "Please talk to a counselor or trusted person immediately. Or connect to a 24x7 text line: https://www.crisistextline.org/"
            ),

            // Closure Node
            ChatNode(
                id = 40,
                message = "It was great talking to you. You’re doing your best, and that’s enough. If you need to talk again, I’m always here. 💬"
            )

        )



    }


}