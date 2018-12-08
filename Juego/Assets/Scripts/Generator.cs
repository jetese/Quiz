using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Generator : MonoBehaviour {
    public GameObject []list;
    private IEnumerator coroutine;
    private float time;
    // Use this for initialization
    void Start () {
        time = 2.0f;
        coroutine = GenerateElementInTime(time);
        StartCoroutine(coroutine);
    }

    // Update is called once per frame
    void Update () {
		
	}

    private IEnumerator GenerateElementInTime(float waitTime)
    {
        while (true)
        {
            yield return new WaitForSeconds(waitTime);
            GenerateElement();
        }
    }

    private void GenerateElement()
    {
        Instantiate(list[Random.Range(0,list.Length)]);
    }
}
