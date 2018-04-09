/*
This represents a set of data that will describe
a genelaogy research event, given the following

I am Christopher Alan Mosher, a genealogy researcher.
In the project I am working on, my "USA Pedigree Project",
I looked in this source
"Descendants of Hugh Mosher and Rebecca Maxson...",
and it has this piece of text,
"MARY MOSHER d. Greenfield, Saratoga Co. NY before 28 March 1814",
and shows this as its citation "Saratoga Co. NY PR 6:11".

Now I will enter records to represent this extraction.
*/



/* standard lookup types */
/* events */
insert into event_type values (1,'birth');
insert into event_type values (2,'death');
/* roles */
insert into role_type values (2,'decedent');
/* references (citations, etc.) */
insert into reference_type values (1,'cites');
/* personae relations */
insert into persona_rel_type values (1,'identical');
insert into persona_rel_type values (2,'parent');
insert into persona_rel_type values (3,'partner');

/* all places */
insert into place values (1,'Madison, WI, USA');
insert into place values (2,'Saratoga Co., NY, USA');
insert into place values (3,'Greenfield, Saratoga, NY, USA');

/* 3 source fragments */
/* this first one represents me (the researcher) and my
genealogy research project */
insert into source values
(
1000,
'Mosher, Christopher Alan',
'Christopher Alan Mosher''s USA Pedigree Project',
NULL,
NULL,
NULL
);

/* 1813 */
insert into dt values (3,1990,NULL,NULL,1990,NULL,NULL,NULL,NULL,0,0);
/* this is the book I am taking my information from */
insert into source values
(
1001,
'Chamberlain, Mildred (Mosher), and Clarenbach, Laura (McGaffey)',
'Descendants of Hugh Mosher and Rebecca Maxson through Seven Generations, Revised Edition',
3,
1, /* place of publ. */
'Clarenbach, Laura M.'
);

/* 1813 */
insert into dt values (4,1813,NULL,NULL,1813,NULL,NULL,NULL,NULL,0,0);
/* this is the source that the book references */
insert into source values
(
1002,
'Saratoga Co. Surrogate Court',
'Probate Records, Box 6, #11',
4,
2, /* jurisdiction place */
''
);

/* before 28 Mar 1814 */
insert into dt values (1,NULL,NULL,NULL,1814,3,28,NULL,NULL,0,0);

/* event 3000, death, in Greenfield, on some date before 28 Mar 1814 */
insert into event values
(
3000,
3, /* place */
1, /* start date (from dt table) */
1, /* end date */
   /* (if end date is same as start date, then it means
   the event happened at one point in time, as opposed to
   during a period of time.) */
2 /* death (from event_type table) */
);

/* Mary Mosher (from the Hugh Mosher book) */
/* generally the name should be in exactly the same
form as the original source, but this is not absolutely
necessary, since the source repesentation could contain
the exact text instead */
insert into persona values (1,'Mary Mosher');

/* role 1, Mary Mosher was the decedent in event 3000 */
insert into role values (1,2,1,3000);

/* assert 1, assertion is role 1, no rationale, source is Hugh Mosher book */
insert into assert values (1,NULL,1,NULL,'',1001,1);

/* assert 2 my assertion is the same as that assertion, in other
words, I agree with what the book says (because I have no reason to
beleive otherwise, and it is a fairly reputable source) */
insert into assert values (2,NULL,1,NULL,'',1000,1);

/* this ties together the two assertions, and indicates that
my assertion came from the books assertion, this is a citation.
This record represents this concept
assertion 1 cites assertion 2 as its source */
insert into reference values (1,1,2,1);

/* Since the book cites Saratoga Co. Probate Records, we need to
indicate that the books assertion cites the assertion from Saratoga
Co. Probate Records. Since I havent directly examined those
Probate Records myself, I dont know what they actually assert,
so I enter the assertion record with NULL for what is being asserted. */
insert into assert values (3,NULL,NULL,NULL,'',1002,1);

/* and this is the citation record itself
assertion 2 cites assertion 3 as its source */
insert into reference values (2,2,3,1);







/*
Now I go to Saratoga Co. and look at the Probate Records
myself, in person.

I find that they actually indicate that Mary Mosher died
on 31 May 1813.

Now I will enter records (and update existing records)
to represent this new search and the information from it.
*/

/* 31 May 1813 */
insert into dt values (2,1813,5,31,1813,5,31,NULL,NULL,0,0);

/* event 3001, death, in Greenfield, on 31 May 1813 */
insert into event values
(
3001,
3, /* place */
2, /* start date (from dt table) */
2, /* end date */
2 /* death (from event_type table) */
);

/* this is a new persona, because it is from a different source */
insert into persona values (2,'Mary Mosher');

/* this role ties together the persona and the event */
insert into role values (2,2,2,3001);

/* since we now KNOW what the Probate Records assert, we
update the assertion record to point to the new info (the role
we just added) */
update assert set role_fk = 2 where assert_pk = 3;

/* if we disproved the orignal source, we would indicate
that by changing the affirmed flag on our previous assertion
like this:
update assert set affirmed = 0 where assert_pk = 2
But in this case, the original source was correct (even though
we found more accurate information somewhere else).
*/

/* I want to assert that I found this new assertion */
insert into assert values (4,NULL,2,NULL,'',1000,1);
/* assertion 4 cites assertion 3 as its source */
insert into reference values (3,4,3,1);

/* I also want to assert that these two Mary Mosher
personae are actually the same historical person */
insert into persona_rel values (1,1,2,1);
/* this is the assertion, with the rationale */
/* I do not cite another assertion for my assertion, because each persona
cites thier source, and this assertion is just my conclusion. */
insert into assert values (5,NULL,NULL,1,'same name, same husband, same death town',1000,1);
