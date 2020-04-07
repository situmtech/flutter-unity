using System;
using UnityEngine;

namespace FlutterUnityPlugin
{
    public class Message
    {
        public int id;
        public string data;

        public static Message FromJson(string json)
        {
            return JsonUtility.FromJson<Message>(json);
        }

        public string ToJson()
        {
            return JsonUtility.ToJson(this);
        }
    }

    public static class Messages
    {
        public static void Send(Message message)
        {
            string data = message.ToJson();

            if (Application.platform == RuntimePlatform.Android)
            {
                using (AndroidJavaClass jc = new AndroidJavaClass("com.glartek.flutter_unity.FlutterUnityPlugin"))
                {
                    jc.CallStatic("onMessage", data);
                }
            }
            else
            {
                // Unsupported platform
            }
        }

        public static Message Receive(string data)
        {
            return Message.FromJson(data);
        }
    }
}
