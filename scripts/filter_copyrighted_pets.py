#!/usr/bin/env python3
"""
Loc pet trong app/assets/data1 va data2: chi GIU ten generic/khong IP ro rang.
Chay: python3 scripts/filter_copyrighted_pets.py          # dry-run
      python3 scripts/filter_copyrighted_pets.py --delete   # xoa that
"""
from __future__ import annotations

import argparse
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "app" / "assets"

# Ten thu muc duoc GIU (khong trung IP noi tieng). Review thu cong truoc khi ship Play Store.
KEEP_NAMES: frozenset[str] = frozenset(
    {
        "Abbie",
        "Beef",
        "Beel",
        "Blublu",
        "Blue",
        "Bomb",
        "Brief Shimeji",
        "Caleb",
        "Cass",
        "Chonkwave",
        "Chuck",
        "Dark man",
        "Elnina",
        "Gi",
        "Glitch",
        "Grim",
        "Herb",
        "Hop",
        "Jelly Doodle",
        "Jonah",
        "Kai",
        "Kiara",
        "Kinito",
        "Kinito2",
        "Kudari",
        "Loop",
        "Lree",
        "Lunar",
        "Mighty",
        "Modified Bear",
        "Neko",
        "Neko Girl",
        "Noob",
        "Odile",
        "Oliver",
        "One",
        "Pink",
        "Queen",
        "Ratiorine",
        "Rave",
        "Ray",
        "Red",
        "Red man",
        "Streibough",
        "Sunstone",
        "Terence",
        "Thistle",
        "Three",
        "Toxic",
        "Two",
        "Victim man",
        "Wendy",
        "X",
        "Y.V",
        "Yellow",
        "Yellow man",
        "Blank Guy",
        "Blue eyes",
        "BongBongie",
        "Fuyo Cloverfield",
        "Gibpuri",
        "Hex",
        "Jack",
        "Lolipop",
        "Merli",
        "Pizzahead",
        "Shiro",
        "Sup Guy",
    }
)


def list_pet_dirs(pack: str) -> list[Path]:
    base = ASSETS / pack
    if not base.is_dir():
        return []
    return sorted(p for p in base.iterdir() if p.is_dir())


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--delete", action="store_true", help="Xoa thu muc pet vi pham")
    args = parser.parse_args()

    to_remove: list[Path] = []
    kept: list[str] = []

    for pack in ("data1", "data2"):
        for path in list_pet_dirs(pack):
            name = path.name
            if name in KEEP_NAMES:
                kept.append(f"{pack}/{name}")
            else:
                to_remove.append(path)

    print(f"GIU LAI: {len(kept)} pet")
    for k in kept:
        print(f"  + {k}")
    print(f"\nXOA: {len(to_remove)} pet (vi pham / IP ro rang)")
    for p in to_remove:
        print(f"  - {p.relative_to(ROOT)}")

    if args.delete:
        for p in to_remove:
            shutil.rmtree(p)
        print(f"\nDa xoa {len(to_remove)} thu muc.")
    else:
        print("\nDry-run. Chay lai voi --delete de xoa that.")


if __name__ == "__main__":
    main()
