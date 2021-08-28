package tacos.data;
import tacos.Taco;
public interface TacoRepository
/*
 *  Taco interface for Taco  operations
 * 	1. Save Taco created by user on design.html page
 * 	2. This is a funcation interface if only 1 abstract method is provided.
 */
{
Taco save(Taco design);
}