using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using System.IO;
public class MenuManager : MonoBehaviour {

	// Use this for initialization
	void Start () {
        string path = Path.Combine(Application.persistentDataPath, "leaderboard.json");
        if (!File.Exists(path))
        {
            File.Create(path);
        }
    }

    // Update is called once per frame
    void Update () {
		
	}
    
    public void Play()
    {
        SceneManager.LoadScene("Game");
    }

    public void Leaderboard()
    {
        SceneManager.LoadScene("Leaderboard");
    }
}
