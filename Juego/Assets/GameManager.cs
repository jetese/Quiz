using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour {
    private int points;
    private int lives;
    public Text pointText;
    public Text lifeText;
	// Use this for initialization
	void Start () {
        points = 0;
        lives = 5;
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void AddPoints()
    {
        points += 1;
        pointText.text = "Puntuacion: " + points;
    }

    public void removeLife()
    {
        lives--;
        if (lives > -1)
        {
            lifeText.text = "Vidas: " + lives;
        }
        else
        {
            lifeText.text = "Game Over";
        }
    }
}
