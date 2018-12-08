using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WhiteCounter : MonoBehaviour {
    private GameManager gameManager;
    // Use this for initialization
    void Start () {
        gameManager = GameObject.Find("GameManager").GetComponent<GameManager>();
    }
	
	// Update is called once per frame
	void Update () {
		
	}

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "White")
        {
            gameManager.AddPoints();
        }
        Destroy(other.gameObject);
    }
}
