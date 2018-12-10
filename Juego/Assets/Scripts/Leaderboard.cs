using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using Assets.JsonClasses;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Leaderboard : MonoBehaviour {

    public bool initalize = false;
    List<SinglePunt> punts;
    public GameObject TextPrefab;
    public GameObject Content;
	// Use this for initialization
	void Start () {

        punts = new List<SinglePunt>();
        if (initalize)
        {
            //SerializeObj();
            DeserializeObj();
            if (punts != null)
            {
                PaintList();
            }
        }
	}

    public void DeserializeObj()
    {
        string path = Path.Combine(Application.persistentDataPath, "leaderboard.json");

        if (File.Exists(path))
        {
            string data = File.ReadAllText(path);
            punts = Newtonsoft.Json.JsonConvert.DeserializeObject<List<SinglePunt>>(data);            
        }
    }

    public void SerializeObj()
    {
        string data = Newtonsoft.Json.JsonConvert.SerializeObject(punts);

        string path = Path.Combine(Application.persistentDataPath, "leaderboard.json");       

        File.WriteAllText(path, data);
    }

    private void PaintList()
    {
        for(int i = 0; i<punts.Count; i++)
        {
            
            GameObject g = Instantiate(TextPrefab, Content.transform);
            foreach(Transform t in g.transform)
            {
                if(t.tag == "name")
                {
                    t.GetComponent<Text>().text = punts[i].name;
                }
                if(t.tag == "punt")
                {
                    t.GetComponent<Text>().text = punts[i].punt.ToString();
                }
            }
        }
    }

    public void AddPunt(int punt, string name = "")
    {
        if(name == "")
        {
            name = "Anonimo";
        }
        DeserializeObj();
        if(punts == null)
        {
            punts = new List<SinglePunt>();
        }
        SinglePunt sp = new SinglePunt(name, punt);
        AddSort(sp);
        SerializeObj();
        print("AddedPunt");
    }

    private void AddSort(SinglePunt p)
    {
        int points = p.punt;
        bool found = false;
        int i = 0;
        while(!found && i < punts.Count)
        {
            if (punts[i].punt < points)
            {
                found = true;
            }
            else
            {
                i++;
            }
        }

        if (found)
        {
            punts.Insert(i, p);
        }
        else if(punts.Count<10)
        {
            punts.Add(p);
        }

    }
	
	public void LoadMenu()
    {
        SceneManager.LoadScene("Menu");
    }
}
