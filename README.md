# pkmnBattle
This is a personal project based on Nintendo's game 
"Pokemon" developed in java utilizing the principals
of object-oriented programming and state patterns

## Controls
<ins> W // ↑</ins> = Move Up

<ins>A // ←</ins> = Move Left

<ins>S // ↓</ins> = Move Down

<ins>D // →</ins> = Move Right

<ins>SPACE // ENTER</ins> = Confirm Selection

<ins>X // ESCAPE</ins> = Cancel Selection

## Game Objective
Create a balanced team consisting of three Pokemon to
defeat your opponent's team of 3

# Mechanics

## Typing
- Each Pokemon has 1-2 types
- Each move a Pokemon can use has a single type
- If an attacking move is used, and it's type matches
the user's type, the move deals 50% extra damage
- Moves can be **Super Effective** (dealing bonus damage),
**Neutral** (dealing expected damage),
**Not Very Effective** (dealing reduced damage), or
**Ineffective** (dealing 0 damage), all depending on
the move's type and the target's type

## Moves
- Each Pokemon has four moves they can use which 
fall into one of three categories
- - <ins>**Physical**</ins> - An attacking move which
deals damage based on the user's **Attack** stat
and the target's **Defense** stat
- - <ins>**Special**</ins> - An attacking move which
  deals damage based on the user's **Special Attack**
stat and the target's **Special Defense** stat
- - <ins>**Status**</ins> - An non-attacking move has
an effect on either the user or the target. Status moves
may increase / decrease stats, heal, or inflict a 
status condition
- Some moves have increased priority, meaning they will 
typically move first in the turn order, regardless of 
the user's speed 
- If a move runs out of power points (PP) it can't 
be used
- If a Pokemon has no usable moves, it will use **Struggle**,
a typeless, physical attacking move that makes the 
user take 25% of their health in recoil damage

## Stats
<ins>Attack</ins> = Offensive stat used to calculate the
damage dealt by **physical** attacks

<ins>Defense</ins> = Defensive stat used to calculate the
damage received by **physical** attacks

<ins>Special Attack</ins> = Offensive stat used to calculate the
damage dealt by **Special** attacks

<ins>Special Defense</ins> = Defensive stat used to calculate the
damage received by **Special** attacks

<ins>Speed</ins> = Stat used to determine turn order. 
The Pokemon with the higher speed stat will typically
move first


## Non-Volatile Status Conditions
A Pokemon may only have one non-volatile status condition at a time

<ins>Burn</ins> = Pokemon affected by burn will take 
1/16 of their max health in damage at the end of each
turn.
Being burnt will also reduce the Pokemon's **Attack** stat by 50%. 

<ins>Poison</ins> = Pokemon affected by poison will 
take increasing damage for every turn they are poisoned,
 calculated with the equation
**( (turns poisoned) / 16 )**

<ins>Sleep</ins> = Pokemon who are asleep are incapable
of attacking for 1-3 turns. Pokemon have a 50% chance of waking up

<ins>Paralysis</ins> = Pokemon affected by paralysis
have a 25% chance of failing to attack. Paralysis
also reduces the affected Pokemon's **Speed** by 50%

<ins>Freeze</ins> = Pokemon affected by freeze have an
80% chance of failing to attack. Successfully attacking
removes the status condition