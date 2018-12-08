using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KeyboardControl : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        float movement = Input.GetAxis("Horizontal");
        transform.Rotate(0, 0, movement * 3f);
    }
}
