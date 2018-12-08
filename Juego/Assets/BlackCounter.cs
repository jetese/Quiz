using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BlackCounter : MonoBehaviour {
    private GameManager gameManager;
	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        gameManager = GameObject.Find("GameManager").GetComponent<GameManager>();
	}

    private void OnTriggerEnter(Collider other)
    {
        if(other.gameObject.tag == "Black")
        {
            gameManager.AddPoints();
        }
        Destroy(other.gameObject);
    }
}
