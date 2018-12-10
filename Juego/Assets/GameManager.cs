using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour {
    private int points;
    private int lives;
    public Text pointText;
    public Text lifeText;

    public GameObject GUI;
    public GameObject GameOver;
    public Text finalPunt;

    public GameObject insertName;
    public GameObject textarea;

    bool retryGame = false;

    private bool playing = true;
	// Use this for initialization
	void Start () {
        points = 0;
        lives = 5;
        GameOver.SetActive(false);
        insertName.SetActive(false);
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void AddPoints()
    {
        if (playing)
        {
            points += 1;
            pointText.text = "Puntuacion: " + points;
        }
    }

    public void removeLife()
    {
        if (playing)
        {
            lives--;
            if (lives > -1)
            {
                lifeText.text = "Vidas: " + lives;
            }
            else
            {
                GameOver.SetActive(true);
                GUI.SetActive(false);
                finalPunt.text = "Puntuacion: " + points;
                playing = false;
            }
        }
    }

    public void Finish(bool retry)
    {
        GameOver.SetActive(false);
        insertName.SetActive(true);
        retryGame = retry;
    }

    public void InsertPunt()
    {
        string name = textarea.GetComponent<InputField>().text;
        GetComponent<Leaderboard>().AddPunt(points, name);
        if (retryGame)
        {
            Retry();
        }
        else
        {
            Menu();
        }
    }

    public void Retry()
    {
        SceneManager.LoadScene("Game");
    }

    public void Menu()
    {
        SceneManager.LoadScene("Menu");
    }
}
