import json
import re
import pandas as pd

# left
left_json = json.load(open(f"all_wiki_airports.json", "r"))["results"]["bindings"]
left = pd.DataFrame([{ k: b[k]["value"] for k in b.keys()} for b in left_json])
left.columns = ["id", "name", "icao"]

(
    left.drop_duplicates()
        .dropna(how="all", subset=["name"])
        .drop("icao", axis=1)
        .to_csv("left.csv", index=False)
)

# right
right_json = json.load(open(f"all_micro_airports.json", "r"))["results"]["bindings"]
right = pd.DataFrame([{ k: b[k]["value"] for k in b.keys()} for b in right_json])
right.columns = ["id", "name", "icao"]

(
    right.drop_duplicates()
        .dropna(how="all", subset=["name"])
        .drop("icao", axis=1)
        .to_csv("right.csv", index=False)
)

# gt
gt = (
    pd.merge(left, right, on="icao", how="inner", suffixes=("_l", "_r"))
        .drop("icao", axis=1)
)

(
    gt.drop_duplicates()
        .dropna(how="all", subset=["name_l", "name_r"])
        .to_csv("gt.csv", index=False)
)

# left_aug
all_wiki_json = json.load(open(f"all_wiki_airports_no_icao.json", "r"))["results"]["bindings"]
all_wiki_df = pd.DataFrame([{ k: b[k]["value"] for k in b.keys() } for b in all_wiki_json])

all_wiki_df.columns = ["id", "name"]
all_wiki_df["id"] = all_wiki_df["id"].apply(lambda x: re.search("/(Q\d+)$", x).group(1))

(all_wiki_df
    .drop_duplicates()
    .dropna(how="all", subset=["name"])
    .to_csv("left_aug.csv", index=False)
)