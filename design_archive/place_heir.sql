select pa.display_name, g.a_rel_b, pb.display_name
from place pa inner join place_group g on (pa.pk=a_fk) inner join place pb on (pb.pk=b_fk)